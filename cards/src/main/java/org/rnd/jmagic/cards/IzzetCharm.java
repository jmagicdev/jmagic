package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Izzet Charm")
@Types({Type.INSTANT})
@ManaCost("UR")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class IzzetCharm extends Card
{
	public IzzetCharm(GameState state)
	{
		super(state);

		{
			// Choose one \u2014 Counter target noncreature spell unless its
			// controller pays (2);
			Target target = this.addTarget(1, RelativeComplement.instance(Spells.instance(), HasType.instance(Type.CREATURE)), "target noncreature spell");

			EventFactory counter = counter(targetedBy(target), "Counter target noncreature spell.");

			SetGenerator controller = ControllerOf.instance(targetedBy(target));
			EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay (2)");
			pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			pay.parameters.put(EventType.Parameter.COST, Identity.instance(new ManaPool("(2)")));
			pay.parameters.put(EventType.Parameter.PLAYER, controller);

			this.addEffect(1, unless(controller, counter, pay, "Counter target noncreature spell unless its controller pays (2)"));
		}

		{
			// or Izzet Charm deals 2 damage to target creature;
			SetGenerator target = targetedBy(this.addTarget(2, CreaturePermanents.instance(), "target creature"));
			this.addEffect(2, spellDealDamage(2, target, "Izzet Charm deals 2 damage to target creature"));
		}

		{
			// or draw two cards, then discard two cards.
			this.addEffect(3, drawCards(You.instance(), 2, "draw two cards,"));
			this.addEffect(3, discardCards(You.instance(), 2, "then discard two cards."));
		}
	}
}
