package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * 609.7a If an effect requires a player to choose a source of damage, he or she
 * may choose a permanent; a spell on the stack (including a permanent spell);
 * any object referred to by an object on the stack, by a replacement or
 * prevention effect that's waiting to apply, or by a delayed triggered ability
 * that's waiting to trigger (even if that object is no longer in the zone it
 * used to be in); or, for certain casual variant games, a face-up card in the
 * command zone.
 * 
 * TODO : any object referred to by a replacement effect that's waiting to apply
 */
public class AllSourcesOfDamage extends SetGenerator
{
	private static AllSourcesOfDamage _instance = null;

	public static AllSourcesOfDamage instance()
	{
		if(_instance == null)
			_instance = new AllSourcesOfDamage();
		return _instance;
	}

	private AllSourcesOfDamage()
	{
		// No code necessary
	}

	private static java.util.Collection<GameObject> referencedBy(GameState state, GameObject object)
	{
		java.util.Collection<GameObject> ret = new java.util.HashSet<GameObject>();

		for(Mode m: object.getModes())
			for(EventFactory effect: m.effects)
				ret.addAll(effect.refersTo(state, object));

		return ret;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		// a permanent;
		for(GameObject object: state.battlefield().objects)
			ret.add(object);

		for(GameObject object: state.stack().objects)
		{
			// a spell on the stack (including a permanent spell);
			if(object.isSpell())
				ret.add(object);

			// any object referred to by an object on the stack,
			ret.addAll(referencedBy(state, object));
		}

		// by a replacement or prevention effect that's waiting to apply,
		for(EventReplacementEffect effect: state.eventReplacementEffects)
			ret.addAll(effect.refersTo(state));

		for(ZoneChangeReplacementEffect effect: state.zoneChangeReplacementEffects)
			ret.addAll(effect.refersTo(state));

		for(DamageReplacementEffect effect: state.damageReplacementEffects)
			ret.addAll(effect.refersTo(state));

		// or by a delayed triggered ability that's waiting to trigger (even if
		// that object is no longer in the zone it used to be in);
		for(java.util.Collection<TriggeredAbility> abilities: state.waitingTriggers.values())
			for(TriggeredAbility ability: abilities)
				ret.addAll(referencedBy(state, ability));

		// or a face-up card in the command zone
		for(GameObject o: state.commandZone().objects)
			if(o.isCard())
				for(Player p: state.players)
					if(!p.outOfGame && o.isVisibleTo(p))
						ret.add(o);

		return ret;
	}

}
