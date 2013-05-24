package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Escaped Null")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.BERSERKER})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class EscapedNull extends Card
{
	public static final class SoapOnARope extends EventTriggeredAbility
	{
		public SoapOnARope(GameState state)
		{
			super(state, "Whenever Escaped Null blocks or becomes blocked, it gets +5/+0 until end of turn.");

			this.addPattern(whenThisBlocks());

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_BLOCKED);
			pattern.put(EventType.Parameter.ATTACKER, ABILITY_SOURCE_OF_THIS);
			this.addPattern(pattern);

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, 5, 0, "It gets +5/+0 until end of turn."));
		}
	}

	public EscapedNull(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Lifelink
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));

		// Whenever Escaped Null blocks or becomes blocked, it gets +5/+0 until
		// end of turn.
		this.addAbility(new SoapOnARope(state));
	}
}
