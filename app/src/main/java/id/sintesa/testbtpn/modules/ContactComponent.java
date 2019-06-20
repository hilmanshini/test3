package id.sintesa.testbtpn.modules;

import com.hilman.tauba.modules.ActivityModule;

import javax.inject.Singleton;

import dagger.Component;
import id.sintesa.testbtpn.MainActivity;


@Component(modules = {ContactModule.class})
@Singleton
public abstract class ContactComponent {
    public abstract void inject(MainActivity mQueueFragment);

    @Component.Builder
    public interface Builder {


        public ContactComponent build();
    }
}