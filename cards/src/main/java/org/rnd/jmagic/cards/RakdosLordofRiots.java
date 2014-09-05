package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Rakdos, Lord of Riots")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("BBRR")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class RakdosLordofRiots extends Card
{
	public static final class RakdosLordofRiotsAbility0 extends StaticAbility
	{
		public RakdosLordofRiotsAbility0(GameState state)
		{
			super(state, "You can't cast Rakdos, Lord of Riots unless an opponent lost life this turn.");

			SimpleEventPattern castRakdos = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			castRakdos.put(EventType.Parameter.OBJECT, This.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(castRakdos));
			this.addEffectPart(part);

			state.ensureTracker(new LifeLostThisTurn.LifeTracker());
			SetGenerator lifeLostByOpponents = LifeLostThisTurn.instance(OpponentsOf.instance(You.instance()));
			this.canApply = Not.instance(Intersect.instance(Between.instance(1, null), lifeLostByOpponents));
		}
	}

	public RakdosLordofRiots(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// You can't cast Rakdos, Lord of Riots unless an opponent lost life
		// this turn.
		this.addAbility(new RakdosLordofRiotsAbility0(state));

		// Flying, trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Creature spells you cast cost (1) less to cast for each 1 life your
		// opponents have lost this turn.
		state.ensureTracker(new LifeLostThisTurn.LifeTracker());
		SetGenerator lifeLost = LifeLostThisTurn.instance(OpponentsOf.instance(You.instance()));
		this.addAbility(new org.rnd.jmagic.abilities.CostsYouLessToCast(state, HasType.instance(Type.CREATURE), "(1)", lifeLost, "Creature spells you cast cost (1) less to cast for each 1 life your opponents have lost this turn."));
	}
}
