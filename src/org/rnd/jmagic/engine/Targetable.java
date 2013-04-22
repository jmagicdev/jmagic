package org.rnd.jmagic.engine;

public interface Targetable
{
	/**
	 * @return A pattern describing the things this object or player can't be
	 * the target of.
	 */
	public SetPattern cantBeTheTargetOf();

	/**
	 * Tells this object or player it can't be the target of some kind of
	 * object.
	 * 
	 * @param what The kinds of objects this object or player can't be the
	 * target of.
	 */
	public void cantBeTheTargetOf(SetPattern what);
}
