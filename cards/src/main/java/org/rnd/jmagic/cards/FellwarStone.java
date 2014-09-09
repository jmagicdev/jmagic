package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fellwar Stone")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({})
public final class FellwarStone extends Card
{
	public static final class FellwarStoneAbility extends ActivatedAbility
	{
		public FellwarStoneAbility(GameState state)
		{
			super(state, "(T): Add to your mana pool one mana of any color that a land an opponent controls could produce.");

			this.costsTap = true;

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;
			SetGenerator opponents = OpponentsOf.instance(You.instance());
			SetGenerator opponentsLand = Intersect.instance(HasType.instance(Type.LAND), ControlledBy.instance(opponents));

			EventType.ParameterMap manaParameters = new EventType.ParameterMap();
			manaParameters.put(EventType.Parameter.SOURCE, thisCard);
			// Remove COLORLESS from the result of CouldBeProducedBy since this
			// ability only checks colors
			manaParameters.put(EventType.Parameter.MANA, RelativeComplement.instance(CouldBeProducedBy.instance(opponentsLand), Identity.instance(ManaSymbol.ManaType.COLORLESS)));
			manaParameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(new EventFactory(EventType.ADD_MANA, manaParameters, "Add to your mana pool one mana of any color that a land an opponent controls could produce"));
		}
	}

	public FellwarStone(GameState state)
	{
		super(state);

		this.addAbility(new FellwarStoneAbility(state));
	}
}
