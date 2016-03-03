package burgerator.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Kevin on 3/3/2016.
 */
public class LoadingDialog {

        private ProgressDialog progress;
        private final Context nContext;

        public LoadingDialog(Context context){
            this.nContext = context;
            progress = new ProgressDialog(nContext);
        }

        public void start(){
            progress.setTitle("Loading...");
            //progress.setMessage("Wait while loading...");
            progress.show();
        }

        public void stop(){progress.dismiss();}
}
