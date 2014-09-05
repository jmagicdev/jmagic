package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Reaper King")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.SCARECROW})
@ManaCost("(2/W)(2/U)(2/B)(2/R)(2/G)")
@Printings({@Printings.Printed(ex = Shadowmoor.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.GREEN, Color.BLACK, Color.RED})
public final class ReaperKing extends Card
{
	public static final class KillStuff extends EventTriggeredAbility
	{
		public KillStuff(GameState state)
		{
			super(state, "Whenever another Scarecrow enters the battlefield under your control, destroy target permanent.");
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), RelativeComplement.instance(HasSubType.instance(SubType.SCARECROW), ABILITY_SOURCE_OF_THIS), You.instance(), false));
			Target target = this.addTarget(Permanents.instance(), "target permanent");
			this.addEffect(destroy(targetedBy(target), "Destroy target permanent."));
		}
	}

	public ReaperKing(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Other Scarecrow creatures you control get +1/+1.
		SetGenerator yourOtherScarecrows = Intersect.instance(HasSubType.instance(SubType.SCARECROW), CREATURES_YOU_CONTROL);
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, yourOtherScarecrows, "Other Scarecrow creatures you control", +1, +1, true));

		// Whenever another Scarecrow enters the battlefield under your control,
		// destroy target permanent.
		this.addAbility(new KillStuff(state));
	}
}
