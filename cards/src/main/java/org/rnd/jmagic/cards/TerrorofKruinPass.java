package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Terror of Kruin Pass")
@Types({Type.CREATURE})
@SubTypes({SubType.WEREWOLF})
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class TerrorofKruinPass extends AlternateCard
{
	/**
	 * @eparam OBJECT: the creatures who cannot be blocked except by two or more
	 * creatures
	 */
	public static final ContinuousEffectType KRUIN_BLOCKING_RESTRICTION = new ContinuousEffectType("KRUIN_BLOCKING_RESTRICTION")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public void apply(GameState state, ContinuousEffect effect, java.util.Map<Parameter, Set> parameters)
		{
			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			{
				SetGenerator countBlocking = Count.instance(Blocking.instance(Identity.instance(object)));
				SetGenerator notAllowed = numberGenerator(1);
				CombatRestriction restriction = new CombatRestriction(Intersect.instance(countBlocking, notAllowed), effect.getSourceObject());
				state.blockingRestrictions.add(restriction);
			}
		}

		@Override
		public Layer layer()
		{
			return Layer.RULE_CHANGE;
		}
	};

	public static final class TerrorofKruinPassAbility1 extends StaticAbility
	{
		public TerrorofKruinPassAbility1(GameState state)
		{
			super(state, "Each Werewolf you control can't be blocked except by two or more creatures.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(KRUIN_BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(HasSubType.instance(SubType.WEREWOLF), CREATURES_YOU_CONTROL));
			this.addEffectPart(part);
		}
	}

	public TerrorofKruinPass(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.setColorIndicator(Color.RED);

		// Double strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.DoubleStrike(state));

		// Each Werewolf you control can't be blocked except by two or more
		// creatures.
		this.addAbility(new TerrorofKruinPassAbility1(state));

		// At the beginning of each upkeep, if a player cast two or more spells
		// last turn, transform Terror of Kruin Pass.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeHuman(state, this.getName()));
	}
}
