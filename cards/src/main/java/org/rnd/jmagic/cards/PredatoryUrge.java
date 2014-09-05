package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Predatory Urge")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class PredatoryUrge extends Card
{
	public static final class PredatoryUrgeAbility1 extends StaticAbility
	{
		public PredatoryUrgeAbility1(GameState state)
		{
			super(state, "Enchanted creature has \"(T): This creature deals damage equal to its power to target creature. That creature deals damage equal to its power to this creature.\"");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), ArenaAbility.class));
		}
	}

	public static final class ArenaAbility extends ActivatedAbility
	{
		public ArenaAbility(GameState state)
		{
			super(state, "(T): This creature deals damage equal to its power to target creature. That creature deals damage equal to its power to this creature.");
			this.costsTap = true;
			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			this.addEffect(permanentDealDamage(PowerOf.instance(ABILITY_SOURCE_OF_THIS), targetedBy(target), "This creature deals damage equal to its power to target creature."));

			EventFactory getHit = new EventFactory(EventType.DEAL_DAMAGE_EVENLY, "That creature deals damage equal to its power to this creature.");
			getHit.parameters.put(EventType.Parameter.SOURCE, targetedBy(target));
			getHit.parameters.put(EventType.Parameter.NUMBER, PowerOf.instance(targetedBy(target)));
			getHit.parameters.put(EventType.Parameter.TAKER, ABILITY_SOURCE_OF_THIS);
			this.addEffect(getHit);
		}
	}

	public PredatoryUrge(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has
		// "(T): This creature deals damage equal to its power to target creature. That creature deals damage equal to its power to this creature."
		this.addAbility(new PredatoryUrgeAbility1(state));
	}
}
