package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Charbelcher")
@Types({Type.ARTIFACT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({})
public final class GoblinCharbelcher extends Card
{
	public static final class GoblinCharbelcherAbility0 extends ActivatedAbility
	{
		public GoblinCharbelcherAbility0(GameState state)
		{
			super(state, "(3), (T): Reveal cards from the top of your library until you reveal a land card. Goblin Charbelcher deals damage equal to the number of nonland cards revealed this way to target creature or player. If the revealed land card was a Mountain, Goblin Charbelcher deals double that damage instead. Put the revealed cards on the bottom of your library in any order.");
			this.setManaCost(new ManaPool("(3)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));

			SetGenerator lands = HasType.instance(Type.LAND);
			SetGenerator revealed = TopMost.instance(LibraryOf.instance(You.instance()), numberGenerator(1), lands);
			this.addEffect(reveal(revealed, "Reveal cards from the top of your library until you reveal a land card."));

			SetGenerator number = Count.instance(RelativeComplement.instance(revealed, lands));
			SetGenerator damage = IfThenElse.instance(Intersect.instance(revealed, lands, HasSubType.instance(SubType.MOUNTAIN)), Multiply.instance(numberGenerator(2), number), number);
			this.addEffect(permanentDealDamage(damage, target, "Goblin Charbelcher deals damage equal to the number of nonland cards revealed this way to target creature or player. If the revealed land card was a Mountain, Goblin Charbelcher deals double that damage instead."));

			EventFactory move = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put the revealed cards on the bottom of your library in any order.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.INDEX, numberGenerator(-1));
			move.parameters.put(EventType.Parameter.OBJECT, revealed);
			this.addEffect(move);
		}
	}

	public GoblinCharbelcher(GameState state)
	{
		super(state);

		// (3), (T): Reveal cards from the top of your library until you reveal
		// a land card. Goblin Charbelcher deals damage equal to the number of
		// nonland cards revealed this way to target creature or player. If the
		// revealed land card was a Mountain, Goblin Charbelcher deals double
		// that damage instead. Put the revealed cards on the bottom of your
		// library in any order.
		this.addAbility(new GoblinCharbelcherAbility0(state));
	}
}
