package eprit.tn.iot;

/**
 * Created by Sami on 14/03/2017.
 */

public interface ServiceCallback<T> {
    void onSuccess(T t);
    void onFail();

}
