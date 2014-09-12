package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Netcaster Spider")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class NetcasterSpider extends Card
{
	public static final class NetcasterSpiderAbility1 extends EventTriggeredAbility
	{
		public NetcasterSpiderAbility1(GameState state)
		{
			super(state, "Whenever Netcaster Spider blocks a creature with flying, Netcaster Spider gets +2/+0 until end of turn.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_BLOCKER);
			pattern.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			pattern.put(EventType.Parameter.ATTACKER, HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
			this.addPattern(pattern);

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +2, +0, "Netcaster Spider gets +2/+0 until end of turn."));
		}
	}

	public NetcasterSpider(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Reach (This creature can block creatures with flying.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));

		// Whenever Netcaster Spider blocks a creature with flying, Netcaster
		// Spider gets +2/+0 until end of turn.
		this.addAbility(new NetcasterSpiderAbility1(state));
	}
}
