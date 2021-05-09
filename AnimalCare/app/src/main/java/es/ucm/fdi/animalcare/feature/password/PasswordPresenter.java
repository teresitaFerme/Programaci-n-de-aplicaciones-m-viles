package es.ucm.fdi.animalcare.feature.password;

import android.content.Context;

import es.ucm.fdi.animalcare.base.BasePresenter;

public class PasswordPresenter extends BasePresenter {
    private PasswordView mPasswordView;
    private PasswordModel mPasswordModel;

    public PasswordPresenter(PasswordView view){
        mPasswordView = view;
        mPasswordModel = new PasswordModel((Context) mPasswordView);
    }

    public void validatePasswords(String username, String opw, String npw1, String npw2){
        if (opw.isEmpty() || npw1.isEmpty() || npw2.isEmpty()){
            mPasswordView.fillField();
            return;
        }

        if (!mPasswordModel.validatePW(username, opw)){
            mPasswordView.incorrectPassword();
            return;
        }

        if (!npw1.equals(npw2)){
            mPasswordView.noPasswordMatch();
            return;
        }

        mPasswordModel.changePassword(username, npw1);
        mPasswordView.changeSuccessful();
    }
}
