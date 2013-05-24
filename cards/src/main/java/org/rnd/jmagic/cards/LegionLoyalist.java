package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Legion Loyalist")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.SOLDIER})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class LegionLoyalist extends Card
{
	public static final class LegionLoyalistAbility1 extends EventTriggeredAbility
	{
		public LegionLoyalistAbility1(GameState state)
		{
			super(state, "Whenever Legion Loyalist and at least two other creatures attack, creatures you control gain first strike and trample until end of turn and can't be blocked by creature tokens this turn.");
			this.addPattern(battalion());

			ContinuousEffect.Part abilities = addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.FirstStrike.class, org.rnd.jmagic.abilities.keywords.Trample.class);

			ContinuousEffect.Part blockRestriction = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			SetGenerator blockingThis = Blocking.instance(CREATURES_YOU_CONTROL);
			SetGenerator creatureTokens = Intersect.instance(HasType.instance(Type.CREATURE), Tokens.instance());
			SetGenerator restriction = Intersect.instance(blockingThis, creatureTokens);
			blockRestriction.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));

			this.addEffect(createFloatingEffect("Creatures you control gain first strike and trample until end of turn and can't be blocked by creature tokens this turn.", abilities, blockRestriction));
		}
	}

	public LegionLoyalist(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Battalion \u2014 Whenever Legion Loyalist and at least two other
		// creatures attack, creatures you control gain first strike and trample
		// until end of turn and can't be blocked by creature tokens this turn.
		this.addAbility(new LegionLoyalistAbility1(state));
	}
}
