package org.rnd.jmagic.engine;

public interface AttachableTo
{
	/**
	 * Add an attachment. Only GameObject instances can attach to
	 * AttachableTo-derived classes
	 */
	public void addAttachment(int attachment);

	public SetPattern cantBeAttachedBy();

	public void cantBeAttachedBy(SetPattern restriction);

	/**
	 * @return A collection of GameObject IDs attached to this.
	 */
	public java.util.Collection<Integer> getAttachments();

	// TODO: push this out to a different interface and make this interface
	// extend it
	/** @return This object or player in the physical game state */
	public AttachableTo getPhysical();

	public boolean isGameObject();

	public void removeAttachment(int attachment);
}
