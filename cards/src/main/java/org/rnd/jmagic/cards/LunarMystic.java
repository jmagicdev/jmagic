package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Lunar Mystic")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class LunarMystic extends Card
{
	public static final class LunarMysticAbility0 extends EventTriggeredAbility
	{
		public LunarMysticAbility0(GameState state)
		{
			super(state, "Whenever you cast an instant spell, you may pay (1). If you do, draw a card.");

			SimpleEventPattern whenYouCastASpell = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			whenYouCastASpell.put(EventType.Parameter.PLAYER, You.instance());
			whenYouCastASpell.put(EventType.Parameter.OBJECT, HasType.instance(Type.INSTANT));
			this.addPattern(whenYouCastASpell);

			this.addEffect(ifThen(youMayPay("(1)"), drawACard(), "You may pay (1). If you do, draw a card."));
		}
	}

	public LunarMystic(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever you cast an instant spell, you may pay (1). If you do, draw
		// a card.
		this.addAbility(new LunarMysticAbility0(state));
	}
}
