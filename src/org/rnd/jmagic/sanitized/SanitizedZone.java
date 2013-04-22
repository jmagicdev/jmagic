package org.rnd.jmagic.sanitized;

public class SanitizedZone extends SanitizedIdentified
{
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.objects == null) ? 0 : this.objects.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(!super.equals(obj))
			return false;
		if(getClass() != obj.getClass())
			return false;
		SanitizedZone other = (SanitizedZone)obj;
		if(this.objects == null)
		{
			if(other.objects != null)
				return false;
		}
		else if(!this.objects.equals(other.objects))
			return false;
		return true;
	}

	private static final long serialVersionUID = 2L;

	public final java.util.List<Integer> objects;

	public SanitizedZone(org.rnd.jmagic.engine.Zone z)
	{
		super(z);

		this.objects = SanitizedGameState.IDs(z);
	}
}
