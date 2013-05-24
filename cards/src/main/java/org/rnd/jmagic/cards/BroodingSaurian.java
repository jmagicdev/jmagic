package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import java.util.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.Set;
import org.rnd.jmagic.engine.generators.*;

@Name("Brooding Saurian")
@Types({Type.CREATURE})
@SubTypes({SubType.LIZARD})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = Expansion.COLDSNAP, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class BroodingSaurian extends Card
{
	/**
	 * @eparam OBJECT: all permanents
	 */
	public static final ContinuousEffectType EACH_PLAYER_TAKES_BACK_THEIR_STUFF = new ContinuousEffectType("EACH_PLAYER_TAKES_BACK_THEIR_STUFF")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public void apply(GameState state, ContinuousEffect effect, Map<Parameter, Set> parameters)
		{
			for(GameObject o: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
				o.setController(o.getOwner(state));
		}

		@Override
		public Layer layer()
		{
			return Layer.CONTROL_CHANGE;
		}
	};

	public static final class BroodingSaurianAbility0 extends EventTriggeredAbility
	{
		public BroodingSaurianAbility0(GameState state)
		{
			super(state, "At the beginning of each end step, each player gains control of all nontoken permanents he or she owns.");
			this.addPattern(atTheBeginningOfEachEndStep());

			ContinuousEffect.Part part = new ContinuousEffect.Part(EACH_PLAYER_TAKES_BACK_THEIR_STUFF);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, RelativeComplement.instance(Permanents.instance(), Tokens.instance()));
			this.addEffect(createFloatingEffect("Each player gains control of all nontoken permanents he or she owns.", part));
		}
	}

	public BroodingSaurian(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// At the beginning of each end step, each player gains control of all
		// nontoken permanents he or she owns.
		this.addAbility(new BroodingSaurianAbility0(state));
	}
}
