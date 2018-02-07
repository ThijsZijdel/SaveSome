package thijszijdel.savesome.interfaces;

public interface State {

    /**
     * Method for checking the objects state
     * @return the state
     */
    public boolean isShowing();

    /**
     * Method for setting the objects state
     *
     * @param state that will be set
     */
    public void setShowing(boolean state);
}
