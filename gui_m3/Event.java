package gui_m3;

/**
 * Created by matthew on 5/31/17.
 */
public class Event implements Comparable
{
    private int priority;
    private String name;
    private int id;

    public Event(String name, int id, int priority)
    {
        this.id = id;
        this.priority = priority;
        this.name = name;
    }

    @Override
    public int compareTo(Object o)
    {
        if (o instanceof Event)
        {
            Event eo = (Event) o;

            if (eo.priority > this.priority)
                return 1;

            if (eo.priority < this.priority)
                return -1;
        }

        return 0;
    }

    @Override
    public boolean equals(Object o)
    {
        if(o instanceof Event)
        {
            Event other = (Event) o;
            if(other.id == this.id)
            {
                if(other.name.equals(this.name))
                {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public String toString()
    {
        return name;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
