package dk.gruppeseks.bodtrd.common.data.entityelements;

/**
 *
 * @author lucas
 */
public class Owner
{
    private int _id = 0;

    public Owner(int ownerId)
    {
        _id = ownerId;
    }

    public int getId()
    {
        return _id;
    }

    public void setId(int id)
    {
        this._id = id;
    }
}
