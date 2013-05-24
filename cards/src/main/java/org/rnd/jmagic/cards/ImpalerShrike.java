package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Impaler Shrike")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class ImpalerShrike extends Card
{
	public static final class ImpalerShrikeAbility1 extends EventTriggeredAbility
	{
		public ImpalerShrikeAbility1(GameState state)
		{
			super(state, "Whenever Impaler Shrike deals combat damage to a player, you may sacrifice it. If you do, draw three cards.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());
			EventFactory factory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may sacrifice it. If you do, draw three cards.");
			factory.parameters.put(EventType.Parameter.IF, Identity.instance(youMay(sacrificeThis("it"), "You may sacrifice it.")));
			factory.parameters.put(EventType.Parameter.THEN, Identity.instance(drawCards(You.instance(), 3, "Draw three cards.")));
			this.addEffect(factory);
		}
	}

	public ImpalerShrike(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Impaler Shrike deals combat damage to a player, you may
		// sacrifice it. If you do, draw three cards.
		this.addAbility(new ImpalerShrikeAbility1(state));
	}
}
