package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Putrid Imp")
@Types({Type.CREATURE})
@SubTypes({SubType.IMP, SubType.ZOMBIE})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class PutridImp extends Card
{
	public static final class PutridImpAbility0 extends ActivatedAbility
	{
		public PutridImpAbility0(GameState state)
		{
			super(state, "Discard a card: Putrid Imp gains flying until end of turn.");
			// Discard a card
			this.addCost(discardCards(You.instance(), 1, "Discard a card"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Flying.class, "Putrid Imp gains flying until end of turn."));
		}
	}

	public static final class PutridImpAbility1 extends StaticAbility
	{
		public PutridImpAbility1(GameState state)
		{
			super(state, "Threshold \u2014 As long as seven or more cards are in your graveyard, Putrid Imp gets +1/+1 and can't block.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +1, +1));

			SetGenerator restriction = Intersect.instance(Blocking.instance(), This.instance());
			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));
			this.addEffectPart(part);

			this.canApply = Both.instance(this.canApply, Threshold.instance());
		}
	}

	public PutridImp(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Discard a card: Putrid Imp gains flying until end of turn.
		this.addAbility(new PutridImpAbility0(state));

		// Threshold \u2014 As long as seven or more cards are in your
		// graveyard, Putrid Imp gets +1/+1 and can't block.
		this.addAbility(new PutridImpAbility1(state));
	}
}
