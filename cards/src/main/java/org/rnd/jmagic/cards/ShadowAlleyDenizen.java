package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Shadow Alley Denizen")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE, SubType.ROGUE})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class ShadowAlleyDenizen extends Card
{
	public static final class ShadowAlleyDenizenAbility0 extends EventTriggeredAbility
	{
		public ShadowAlleyDenizenAbility0(GameState state)
		{
			super(state, "Whenever another black creature enters the battlefield under your control, target creature gains intimidate until end of turn.");

			SetGenerator blackCreatures = Intersect.instance(HasColor.instance(Color.BLACK), CreaturePermanents.instance());
			SetGenerator otherBlackCreatures = RelativeComplement.instance(blackCreatures, ABILITY_SOURCE_OF_THIS);
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), otherBlackCreatures, You.instance(), false));

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Intimidate.class, "Target creature gains intimidate until end of turn."));
		}
	}

	public ShadowAlleyDenizen(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever another black creature enters the battlefield under your
		// control, target creature gains intimidate until end of turn. (It
		// can't be blocked except by artifact creatures and/or creatures that
		// share a color with it.)
		this.addAbility(new ShadowAlleyDenizenAbility0(state));
	}
}
