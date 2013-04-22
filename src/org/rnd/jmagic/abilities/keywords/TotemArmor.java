package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

/**
 * 702.86. Totem Armor
 * 
 * 702.86a Totem armor is a static ability that appears on some Auras.
 * "Totem armor" means "If enchanted permanent would be destroyed, instead
 * remove all damage marked on it and destroy this Aura."
 */
public final class TotemArmor extends Keyword
{
	public TotemArmor(GameState state)
	{
		super(state, "Totem armor");
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		return java.util.Collections.<StaticAbility>singletonList(new TotemArmorAbility(this.state));
	}

	/**
	 * @eparam OBJECT: the object to remove damage from
	 * @eparam RESULT: empty
	 */
	public static final EventType TOTEM_ARMOR_EFFECT = new EventType("TOTEM_ARMOR_EFFECT")
	{

		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.setResult(Empty.set);

			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
				object.getPhysical().setDamage(0);

			return true;
		}
	};

	public static final class TotemArmorAbility extends StaticAbility
	{

		public TotemArmorAbility(GameState state)
		{
			super(state, "If enchanted permanent would be destroyed, instead remove all damage marked on it and destroy this Aura.");

			SetGenerator enchantedPermanent = EnchantedBy.instance(This.instance());

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DESTROY_ONE_PERMANENT);
			pattern.put(EventType.Parameter.PERMANENT, enchantedPermanent);

			EventReplacementEffect replacement = new EventReplacementEffect(state.game, "If enchanted permanent would be destroyed, instead remove all damage marked on it and destroy this Aura.", pattern);

			EventFactory removeDamage = new EventFactory(TOTEM_ARMOR_EFFECT, "Remove all damage marked on it.");
			removeDamage.parameters.put(EventType.Parameter.OBJECT, enchantedPermanent);
			replacement.addEffect(removeDamage);

			replacement.addEffect(destroy(This.instance(), "Destroy this Aura."));

			this.addEffectPart(replacementEffectPart(replacement));
		}
	}
}
