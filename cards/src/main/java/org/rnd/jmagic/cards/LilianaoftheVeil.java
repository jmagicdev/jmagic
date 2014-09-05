package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Liliana of the Veil")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.LILIANA})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLACK})
public final class LilianaoftheVeil extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("LilianaoftheVeil", "Choose a pile of permanents to sacrifice.", true);

	public static final class LilianaoftheVeilAbility0 extends LoyaltyAbility
	{
		public LilianaoftheVeilAbility0(GameState state)
		{
			super(state, +1, "Each player discards a card.");
			this.addEffect(discardCards(Players.instance(), 1, "Each player discards a card."));
		}
	}

	public static final class LilianaoftheVeilAbility1 extends LoyaltyAbility
	{
		public LilianaoftheVeilAbility1(GameState state)
		{
			super(state, -2, "Target player sacrifices a creature.");
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(sacrifice(target, 1, CreaturePermanents.instance(), "Target player sacrifices a creature."));
		}
	}

	public static final class LilianaoftheVeilAbility2 extends LoyaltyAbility
	{
		public LilianaoftheVeilAbility2(GameState state)
		{
			super(state, -6, "Separate all permanents target player controls into two piles. That player sacrifices all permanents in the pile of his or her choice.");

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

			EventFactory piles = separateIntoPiles(You.instance(), ControlledBy.instance(target), 2, "Separate all permanents target player controls into two piles.");
			this.addEffect(piles);

			EventFactory choose = playerChoose(target, 1, EffectResult.instance(piles), PlayerInterface.ChoiceType.PILE, REASON, "That player chooses a pile.");
			this.addEffect(choose);

			EventFactory sacrifice = new EventFactory(EventType.SACRIFICE_PERMANENTS, "That player sacrifices all permanents in the pile of his or her choice.");
			sacrifice.parameters.put(EventType.Parameter.CAUSE, This.instance());
			sacrifice.parameters.put(EventType.Parameter.PLAYER, target);
			sacrifice.parameters.put(EventType.Parameter.PERMANENT, ExplodeCollections.instance(EffectResult.instance(choose)));
			this.addEffect(sacrifice);
		}
	}

	public LilianaoftheVeil(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(3);

		// +1: Each player discards a card.
		this.addAbility(new LilianaoftheVeilAbility0(state));

		// -2: Target player sacrifices a creature.
		this.addAbility(new LilianaoftheVeilAbility1(state));

		// -6: Separate all permanents target player controls into two piles.
		// That player sacrifices all permanents in the pile of his or her
		// choice.
		this.addAbility(new LilianaoftheVeilAbility2(state));
	}
}
