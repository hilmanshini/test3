package id.sintesa.testbtpn.modules;

import dagger.Module;
import dagger.Provides;
import testbtpnmodule.rest.ApiConfig;
import testbtpnmodule.rest.ApiService;
import testbtpnmodule.rest.ApiServiceImpl;
import testbtpnmodule.service.ContactService;
import testbtpnmodule.service.ContactServiceImpl;

@Module
public class ContactModule {

    @Provides
    public ApiConfig mApiConfig() {
        return new ApiConfig();
    }

    @Provides
    public ApiService mApiService(ApiConfig mApiConfig) {
        return new ApiServiceImpl(mApiConfig);
    }

    @Provides
    public ContactService mContactService(ApiService mApiService) {
        return new ContactServiceImpl(mApiService);
    }
}
