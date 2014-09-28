package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Necropolis Fiend")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("7BB")
@ColorIdentity({Color.BLACK})
public final class NecropolisFiend extends Card
{
	public static final class NecropolisFiendAbility2 extends ActivatedAbility
	{
		public NecropolisFiendAbility2(GameState state)
		{
			super(state, "(X), (T), Exile X cards from your graveyard: Target creature gets -X/-X until end of turn.");
			this.setManaCost(new ManaPool("(X)"));
			this.costsTap = true;

			SetGenerator X = ValueOfX.instance(This.instance());
			this.addCost(exile(You.instance(), InZone.instance(GraveyardOf.instance(You.instance())), X, "Exile X cards from your graveyard"));

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			SetGenerator minusX = Subtract.instance(numberGenerator(0), X);
			this.addEffect(ptChangeUntilEndOfTurn(target, minusX, minusX, "Target creature gets -X/-X until end of turn."));
		}
	}

	public NecropolisFiend(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// Delve (Each card you exile from your graveyard while casting this
		// spell pays for (1).)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Delve(state));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (X), (T), Exile X cards from your graveyard: Target creature gets
		// -X/-X until end of turn.
		this.addAbility(new NecropolisFiendAbility2(state));
	}
}
