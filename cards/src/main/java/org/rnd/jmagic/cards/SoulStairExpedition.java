package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Soul Stair Expedition")
@Types({Type.ENCHANTMENT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class SoulStairExpedition extends Card
{
	public static final class DoubleRaiseDead extends ActivatedAbility
	{
		public DoubleRaiseDead(GameState state)
		{
			super(state, "Remove three quest counters from Soul Stair Expedition and sacrifice it: Return up to two target creature cards from your graveyard to your hand.");
			this.addCost(removeCountersFromThis(3, Counter.CounterType.QUEST, "Soul Stair Expedition"));
			this.addCost(sacrificeThis("Soul Stair Expedition"));

			SetGenerator yourYard = GraveyardOf.instance(You.instance());
			SetGenerator inYourYard = InZone.instance(yourYard);
			SetGenerator creaturesInYourYard = Intersect.instance(HasType.instance(Type.CREATURE), inYourYard);
			Target target = this.addTarget(creaturesInYourYard, "target up to two target creature cards from your graveyard");
			target.setNumber(0, 2);

			EventFactory effect = new EventFactory(EventType.MOVE_OBJECTS, "Return up to two target creature cards from your graveyard to your hand.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			effect.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			this.addEffect(effect);
		}
	}

	public SoulStairExpedition(GameState state)
	{
		super(state);

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, you may put a quest counter on Soul Stair Expedition.
		this.addAbility(new org.rnd.jmagic.abilities.LandfallForQuestCounter(state, this.getName()));

		// Remove three quest counters from Soul Stair Expedition and sacrifice
		// it: Return up to two target creature cards from your graveyard to
		// your hand.
		this.addAbility(new DoubleRaiseDead(state));
	}
}
