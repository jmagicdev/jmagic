package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Grappling Hook")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.RARE)})
@ColorIdentity({})
public final class GrapplingHook extends Card
{
	public static final class GiveDoubleStrike extends StaticAbility
	{
		public GiveDoubleStrike(GameState state)
		{
			super(state, "Equipped creature has double strike.");
			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.DoubleStrike.class));
		}
	}

	public static final class Grapple extends EventTriggeredAbility
	{
		public Grapple(GameState state)
		{
			super(state, "Whenever equipped creature attacks, you may have target creature block it this turn if able.");

			SetGenerator equippedCreature = EquippedBy.instance(AbilitySource.instance(This.instance()));

			SimpleEventPattern whenEquippedCreatureAttacks = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			whenEquippedCreatureAttacks.put(EventType.Parameter.OBJECT, equippedCreature);
			this.addPattern(whenEquippedCreatureAttacks);

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, equippedCreature);
			part.parameters.put(ContinuousEffectType.Parameter.DEFENDING, targetedBy(target));

			this.addEffect(youMay(createFloatingEffect("Target creature blocks equipped creature this turn if able.", part), "You may have target creature block equipped creature this turn if able."));
		}
	}

	public GrapplingHook(GameState state)
	{
		super(state);

		// Equipped creature has double strike.
		this.addAbility(new GiveDoubleStrike(state));

		// Whenever equipped creature attacks, you may have target creature
		// block it this turn if able.
		this.addAbility(new Grapple(state));

		// Equip (4)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(4)"));
	}
}
