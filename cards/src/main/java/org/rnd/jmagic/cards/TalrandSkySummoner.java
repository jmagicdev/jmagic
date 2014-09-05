package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Talrand, Sky Summoner")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.MERFOLK})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class TalrandSkySummoner extends Card
{
	public static final class TalrandSkySummonerAbility0 extends EventTriggeredAbility
	{
		public TalrandSkySummonerAbility0(GameState state)
		{
			super(state, "Whenever you cast an instant or sorcery spell, put a 2/2 blue Drake creature token with flying onto the battlefield.");

			SetGenerator instantOrSorcery = HasType.instance(Type.INSTANT, Type.SORCERY);
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.put(EventType.Parameter.OBJECT, Intersect.instance(instantOrSorcery, Spells.instance()));
			this.addPattern(pattern);

			CreateTokensFactory factory = new CreateTokensFactory(1, 2, 2, "Put a 2/2 blue Drake creature token with flying onto the battlefield.");
			factory.setColors(Color.BLUE);
			factory.setSubTypes(SubType.DRAKE);
			factory.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(factory.getEventFactory());
		}
	}

	public TalrandSkySummoner(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever you cast an instant or sorcery spell, put a 2/2 blue Drake
		// creature token with flying onto the battlefield.
		this.addAbility(new TalrandSkySummonerAbility0(state));
	}
}
