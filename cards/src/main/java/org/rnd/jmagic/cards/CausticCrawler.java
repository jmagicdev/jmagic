package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Caustic Crawler")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class CausticCrawler extends Card
{
	public static final class CausticCrawlerAbility0 extends EventTriggeredAbility
	{
		public CausticCrawlerAbility0(GameState state)
		{
			super(state, "Whenever a land enters the battlefield under your control, you may have target creature get -1/-1 until end of turn.");
			this.addPattern(landfall());

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			EventFactory effect = ptChangeUntilEndOfTurn(target, -1, -1, "Target creature gets -1/-1 until end of turn");
			this.addEffect(youMay(effect, "You may have target creature get -1/-1 until end of turn."));
		}
	}

	public CausticCrawler(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, you may have target creature get -1/-1 until end of turn.
		this.addAbility(new CausticCrawlerAbility0(state));
	}
}
