package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Grand Arbiter Augustin IV")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ADVISOR})
@ManaCost("2WU")
@Printings({@Printings.Printed(ex = Dissension.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class GrandArbiterAugustinIV extends Card
{
	public static final class OpponentsSpellsCostMore extends StaticAbility
	{
		public OpponentsSpellsCostMore(GameState state)
		{
			super(state, "Spells your opponents cast cost (1) more to cast.");

			SetGenerator opponentsStuff = ControlledBy.instance(OpponentsOf.instance(You.instance()), Stack.instance());
			SetGenerator opponentsSpells = Intersect.instance(Spells.instance(), opponentsStuff);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COST_ADDITION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, opponentsSpells);
			part.parameters.put(ContinuousEffectType.Parameter.COST, numberGenerator(1));
			this.addEffectPart(part);
		}
	}

	public GrandArbiterAugustinIV(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// White spells you cast cost (1) less to cast.
		SetGenerator whiteSpells = Intersect.instance(HasColor.instance(Color.WHITE), Spells.instance());
		this.addAbility(new org.rnd.jmagic.abilities.CostsYouLessToCast(state, whiteSpells, "(1)", "White spells you cast cost (1) less to cast."));

		// Blue spells you cast cost (1) less to cast.
		SetGenerator blueSpells = Intersect.instance(HasColor.instance(Color.BLUE), Spells.instance());
		this.addAbility(new org.rnd.jmagic.abilities.CostsYouLessToCast(state, blueSpells, "(1)", "Blue spells you cast cost (1) less to cast."));

		// Spells your opponents cast cost (1) more to cast.
		this.addAbility(new OpponentsSpellsCostMore(state));
	}
}
