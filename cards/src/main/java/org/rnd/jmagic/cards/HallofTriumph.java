package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Hall of Triumph")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = JourneyIntoNyx.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class HallofTriumph extends Card
{
	public static final class ChooseAColor extends org.rnd.jmagic.abilityTemplates.AsThisEntersTheBattlefieldChooseAColor
	{
		public ChooseAColor(GameState state)
		{
			super(state, "As Hall of Triumph enters the battlefield, choose a color.");
			this.getLinkManager().addLinkClass(HallofTriumphAbility1.class);
		}
	}

	public static final class HallofTriumphAbility1 extends StaticAbility
	{
		public HallofTriumphAbility1(GameState state)
		{
			super(state, "Creatures you control of the chosen color get +1/+1.");
			this.getLinkManager().addLinkClass(ChooseAColor.class);

			SetGenerator guys = Intersect.instance(CREATURES_YOU_CONTROL, HasColor.instance(ChosenFor.instance(LinkedTo.instance(Identity.instance(this)))));
			this.addEffectPart(modifyPowerAndToughness(guys, +1, +1));
		}
	}

	public HallofTriumph(GameState state)
	{
		super(state);

		// As Hall of Triumph enters the battlefield, choose a color.
		this.addAbility(new ChooseAColor(state));

		// Creatures you control of the chosen color get +1/+1.
		this.addAbility(new HallofTriumphAbility1(state));
	}
}
