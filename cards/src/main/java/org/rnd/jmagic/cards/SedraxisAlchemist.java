package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Sedraxis Alchemist")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.ZOMBIE})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Conflux.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class SedraxisAlchemist extends Card
{
	public static final class Alchemy extends EventTriggeredAbility
	{
		public Alchemy(GameState state)
		{
			super(state, "When Sedraxis Alchemist enters the battlefield, if you control a blue permanent, return target nonland permanent to its owner's hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			this.interveningIf = Intersect.instance(ControlledBy.instance(You.instance()), HasColor.instance(Color.BLUE));

			Target t = this.addTarget(RelativeComplement.instance(Permanents.instance(), LandPermanents.instance()), "target nonland permanent");
			this.addEffect(bounce(targetedBy(t), "Return target nonland permanent to its owner's hand."));
		}
	}

	public SedraxisAlchemist(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Sedraxis Alchemist enters the battlefield, if you control a blue
		// permanent, return target nonland permanent to its owner's hand.
		this.addAbility(new Alchemy(state));
	}
}
