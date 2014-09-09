package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Sigarda, Host of Herons")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("2GWW")
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class SigardaHostofHerons extends Card
{
	public static final class SigardaHostofHeronsAbility1 extends StaticAbility
	{
		public SigardaHostofHeronsAbility1(GameState state)
		{
			super(state, "Spells and abilities your opponents control can't cause you to sacrifice permanents.");

			SimpleEventPattern youSacrificePermanents = new SimpleEventPattern(EventType.SACRIFICE_ONE_PERMANENT);
			youSacrificePermanents.put(EventType.Parameter.CAUSE, ControlledBy.instance(OpponentsOf.instance(You.instance()), Stack.instance()));
			youSacrificePermanents.put(EventType.Parameter.PERMANENT, Permanents.instance());
			youSacrificePermanents.put(EventType.Parameter.PLAYER, You.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(youSacrificePermanents));
			this.addEffectPart(part);
		}
	}

	public SigardaHostofHerons(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying, hexproof
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Hexproof(state));

		// Spells and abilities your opponents control can't cause you to
		// sacrifice permanents.
		this.addAbility(new SigardaHostofHeronsAbility1(state));
	}
}
