package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Captain of the Mists")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class CaptainoftheMists extends Card
{
	public static final class CaptainoftheMistsAbility0 extends EventTriggeredAbility
	{
		public CaptainoftheMistsAbility0(GameState state)
		{
			super(state, "Whenever another Human enters the battlefield under your control, untap Captain of the Mists.");
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), RelativeComplement.instance(Intersect.instance(HasSubType.instance(SubType.HUMAN), CREATURES_YOU_CONTROL), ABILITY_SOURCE_OF_THIS), You.instance(), false));
			this.addEffect(untap(ABILITY_SOURCE_OF_THIS, "Untap Captain of the Mists."));
		}
	}

	public static final class CaptainoftheMistsAbility1 extends ActivatedAbility
	{
		public CaptainoftheMistsAbility1(GameState state)
		{
			super(state, "(1)(U), (T): You may tap or untap target permanent.");
			this.setManaCost(new ManaPool("(1)(U)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));

			this.addEffect(youMay(tapOrUntap(target, "You may tap or untap target permanent.")));
		}
	}

	public CaptainoftheMists(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Whenever another Human enters the battlefield under your control,
		// untap Captain of the Mists.
		this.addAbility(new CaptainoftheMistsAbility0(state));

		// (1)(U), (T): You may tap or untap target permanent.
		this.addAbility(new CaptainoftheMistsAbility1(state));
	}
}
