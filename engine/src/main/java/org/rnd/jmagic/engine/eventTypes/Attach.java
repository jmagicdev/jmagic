package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Attach extends EventType
{
	public static final EventType INSTANCE = new Attach();

	private Attach()
	{
		super("ATTACH");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		AttachableTo targetObject = parameters.get(Parameter.TARGET).getOne(AttachableTo.class);

		if(targetObject == null)
			return false;

		for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			if(!object.canAttachTo(game, targetObject))
				return false;

		return true;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		boolean attachedAll = true;
		java.util.Set<GameObject> attachments = parameters.get(Parameter.OBJECT).getAll(GameObject.class);
		AttachableTo target = parameters.get(Parameter.TARGET).getOne(AttachableTo.class);
		if(target == null || (target.isGameObject() && ((GameObject)target).isGhost()))
		{
			event.setResult(Empty.set);
			return true;
		}
		int attachmentID = ((Identified)target).ID;

		// a collection of things that are already attached to the specified
		// object or player
		java.util.Collection<Integer> reAttachments = new java.util.LinkedList<Integer>();
		Set detachables = new Set();
		for(GameObject o: attachments)
			if(o.getAttachedTo() != -1)
			{
				if(o.getAttachedTo() == attachmentID)
					reAttachments.add(o.ID);
				else
					detachables.add(o);
			}

		// here we check to make sure the given object(s) can attach to their
		// targets. however if the object is a resolving Aura spell, it's not up
		// to us to say that -- it's already been determined before we enter
		// this function. this is important because the Aura is still in the
		// void when this event is performed, and if it's being granted an
		// enchant keyword by an effect (like Bestow), that effect won't be
		// applying to the Aura yet.
		if(!parameters.containsKey(Parameter.RESOLVING))
		{
			java.util.Iterator<GameObject> iter = attachments.iterator();
			while(iter.hasNext())
			{
				GameObject o = iter.next();
				if(!o.canAttachTo(game, target))
				{
					detachables.remove(o);
					iter.remove();
					attachedAll = false;
				}
			}
		}

		if(!detachables.isEmpty())
		{
			java.util.Map<Parameter, Set> detachParameters = new java.util.HashMap<Parameter, Set>();
			detachParameters.put(Parameter.OBJECT, detachables);
			createEvent(game, "Unattach before attaching", EventType.UNATTACH, detachParameters).perform(event, false);
		}

		for(GameObject o: attachments)
		{
			target.getPhysical().addAttachment(o.ID);
			o.getPhysical().setAttachedTo(attachmentID);

			// 613.6d If an Aura, Equipment, or Fortification becomes
			// attached to an object or player, the Aura, Equipment, or
			// Fortification receives a new timestamp at that time.
			if(!reAttachments.contains(o.ID))
				event.addToNeedsNewTimestamps(o);
		}
		event.setResult(Identity.instance(target));
		return attachedAll;
	}
}