package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Mystic Remora")
@Types({Type.ENCHANTMENT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.ICE_AGE, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class MysticRemora extends Card
{
	public static final class MysticRemoraAbility1 extends EventTriggeredAbility
	{
		public MysticRemoraAbility1(GameState state)
		{
			super(state, "Whenever an opponent casts a noncreature spell, you may draw a card unless that player pays (4).");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			pattern.put(EventType.Parameter.OBJECT, RelativeComplement.instance(Spells.instance(), HasType.instance(Type.CREATURE)));
			this.addPattern(pattern);

			SetGenerator thatPlayer = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.PLAYER);

			EventFactory mayDraw = youMay(drawCards(You.instance(), 1, "Draw a card"), "You may draw a card");

			EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay (4)");
			pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			pay.parameters.put(EventType.Parameter.PLAYER, thatPlayer);
			pay.parameters.put(EventType.Parameter.MANA, Identity.instance(new ManaPool("4")));
			this.addEffect(unless(thatPlayer, mayDraw, pay, "You may draw a card unless that player pays (4)."));
		}
	}

	public MysticRemora(GameState state)
	{
		super(state);

		// Cumulative upkeep (1)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.CumulativeUpkeep.ForMana(state, "(1)"));

		// Whenever an opponent casts a noncreature spell, you may draw a card
		// unless that player pays (4).
		this.addAbility(new MysticRemoraAbility1(state));
	}
}
