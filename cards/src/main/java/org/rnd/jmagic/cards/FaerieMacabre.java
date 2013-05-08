package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Faerie Macabre")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.FAERIE})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class FaerieMacabre extends Card
{
	public static final class FaerieMacabreAbility1 extends ActivatedAbility
	{
		public FaerieMacabreAbility1(GameState state)
		{
			super(state, "Discard Faerie Macabre: Exile up to two target cards from graveyards.");

			// Discard Faerie Macabre
			EventFactory cost = new EventFactory(EventType.DISCARD_CARDS, "Discard Faerie Macabre");
			cost.parameters.put(EventType.Parameter.CAUSE, This.instance());
			cost.parameters.put(EventType.Parameter.CARD, ABILITY_SOURCE_OF_THIS);
			this.addCost(cost);

			Target target = this.addTarget(InZone.instance(GraveyardOf.instance(Players.instance())), "up to two target cards in graveyards");
			target.setNumber(0, 2);

			this.addEffect(exile(targetedBy(target), "Exile up to two target cards from graveyards."));
		}
	}

	public FaerieMacabre(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Discard Faerie Macabre: Exile up to two target cards from graveyards.
		this.addAbility(new FaerieMacabreAbility1(state));
	}
}
