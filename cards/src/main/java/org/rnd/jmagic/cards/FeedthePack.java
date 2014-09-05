package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Feed the Pack")
@Types({Type.ENCHANTMENT})
@ManaCost("5G")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class FeedthePack extends Card
{
	public static final class FeedthePackAbility0 extends EventTriggeredAbility
	{
		public FeedthePackAbility0(GameState state)
		{
			super(state, "At the beginning of your end step, you may sacrifice a nontoken creature. If you do, put X 2/2 green Wolf creature tokens onto the battlefield, where X is the sacrificed creature's toughness.");
			this.addPattern(atTheBeginningOfYourEndStep());

			SetGenerator nontokenCreature = Intersect.instance(NonToken.instance(), CreaturePermanents.instance());

			EventFactory sacrifice = sacrifice(You.instance(), 1, nontokenCreature, "Sacrifice a nontoken creature.");
			SetGenerator thatCreature = OldObjectOf.instance(EffectResult.instance(sacrifice));

			CreateTokensFactory factory = new CreateTokensFactory(ToughnessOf.instance(thatCreature), "Put X 2/2 green Wolf creature tokens onto the battlefield, where X is the sacrificed creature's toughness.");
			factory.addCreature(2, 2);
			factory.setColors(Color.GREEN);
			factory.setSubTypes(SubType.WOLF);

			this.addEffect(ifThen(youMay(sacrifice), factory.getEventFactory(), "You may sacrifice a nontoken creature. If you do, put X 2/2 green Wolf creature tokens onto the battlefield, where X is the sacrificed creature's toughness."));
		}
	}

	public FeedthePack(GameState state)
	{
		super(state);

		// At the beginning of your end step, you may sacrifice a nontoken
		// creature. If you do, put X 2/2 green Wolf creature tokens onto the
		// battlefield, where X is the sacrificed creature's toughness.
		this.addAbility(new FeedthePackAbility0(state));
	}
}
