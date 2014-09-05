package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Jace's Phantasm")
@Types({Type.CREATURE})
@SubTypes({SubType.ILLUSION})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class JacesPhantasm extends Card
{
	public static final class JacesPhantasmAbility1 extends StaticAbility
	{
		public JacesPhantasmAbility1(GameState state)
		{
			super(state, "Jace's Phantasm gets +4/+4 as long as an opponent has ten or more cards in his or her graveyard.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +4, +4));

			DynamicEvaluation dynamic = DynamicEvaluation.instance();
			this.canApply = Both.instance(this.canApply, Intersect.instance(ForEach.instance(OpponentsOf.instance(You.instance()), Count.instance(InZone.instance(GraveyardOf.instance(dynamic))), dynamic), Between.instance(10, null)));
		}
	}

	public JacesPhantasm(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Jace's Phantasm gets +4/+4 as long as an opponent has ten or more
		// cards in his or her graveyard.
		this.addAbility(new JacesPhantasmAbility1(state));
	}
}
