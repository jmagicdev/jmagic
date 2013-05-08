package org.rnd.jmagic.engine;

public class EventReplacementEffectStopper
{
	private java.util.Collection<EventPattern> stopFromReplacing;
	private java.util.Collection<EventPattern> replaceWith;
	private int sourceID;

	/**
	 * Constructs a new event replacement effect stopper.
	 * 
	 * @param thisObject The object this stopper is printed on.
	 * @param stopFromReplacing The events that this stopper stops replacement
	 * effects from replacing (null if it doesn't matter what those effects are
	 * replacing).
	 * @param replaceWith The events that, if a replacement effect would produce
	 * those events, that effect will be stopped.
	 */
	public EventReplacementEffectStopper(GameObject thisObject, EventPattern stopFromReplacing, EventPattern replaceWith)
	{
		this.sourceID = thisObject.ID;

		if(stopFromReplacing == null)
			this.stopFromReplacing = null;
		else
		{
			this.stopFromReplacing = new java.util.LinkedList<EventPattern>();
			this.stopFromReplacing.add(stopFromReplacing);
		}

		if(replaceWith == null)
			this.replaceWith = null;
		else
		{
			this.replaceWith = new java.util.LinkedList<EventPattern>();
			this.replaceWith.add(replaceWith);
		}
	}

	public boolean stops(EventReplacementEffect effect, Event event)
	{
		// slight optimization: we only set the value of thisObject if we need
		// to
		GameObject thisObject = null;

		// Only check events to replace if any were specified
		if(this.stopFromReplacing != null)
		{
			thisObject = event.state.get(this.sourceID);

			boolean canStopThisEvent = false;
			for(EventPattern stopFromReplacing: this.stopFromReplacing)
				if(stopFromReplacing.match(event, thisObject, event.state))
				{
					canStopThisEvent = true;
					break;
				}
			if(!canStopThisEvent)
				return false;
		}

		// Only check resultant events if any were specified
		if(this.replaceWith != null)
		{
			if(thisObject == null)
				thisObject = event.state.getByIDObject(this.sourceID);

			for(EventPattern stop: this.replaceWith)
				for(EventFactory factory: effect)
					if(stop.match(factory.createEvent(event.game, (GameObject)effect.getSourceObject(event.state)), thisObject, event.state))
						return true;
			return false;
		}

		// If no replacement events were specified, and either no trigger events
		// were specified or at least one trigger event was satisfied, this
		// stopper should match
		return true;
	}
}
