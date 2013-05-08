package org.rnd.jmagic.engine;

public class TextChange
{
	public Enum<?> from;
	public Enum<?> to;

	public TextChange(Enum<?> from, Enum<?> to)
	{
		this.from = from;
		this.to = to;
	}

	@SuppressWarnings("unchecked")
	public void applyTo(java.util.Collection<?> ret)
	{
		java.util.Iterator<?> i = ret.iterator();
		boolean removed = false;
		while(i.hasNext())
		{
			Object t = i.next();
			if(t.equals(this.from))
			{
				i.remove();
				removed = true;
				break;
			}
		}
		if(removed)
			((java.util.Collection<Enum<?>>)ret).add(this.to);
	}
}
