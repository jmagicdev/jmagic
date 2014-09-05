package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Dragonmaster Outcast")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SHAMAN})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.RED})
public final class DragonmasterOutcast extends Card
{
	public static final class UpkeepDragons extends EventTriggeredAbility
	{
		public UpkeepDragons(GameState state)
		{
			super(state, "At the beginning of your upkeep, if you control six or more lands, put a 5/5 red Dragon creature token with flying onto the battlefield.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator yourLands = Intersect.instance(LandPermanents.instance(), ControlledBy.instance(You.instance()));
			SetGenerator sixOrMore = Intersect.instance(Between.instance(6, null), Count.instance(yourLands));
			this.interveningIf = sixOrMore;

			CreateTokensFactory token = new CreateTokensFactory(1, 5, 5, "Put a 5/5 red Dragon creature token with flying onto the battlefield.");
			token.setColors(Color.RED);
			token.setSubTypes(SubType.DRAGON);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(token.getEventFactory());
		}
	}

	public DragonmasterOutcast(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// At the beginning of your upkeep, if you control six or more lands,
		// put a 5/5 red Dragon creature token with flying onto the battlefield.
		this.addAbility(new UpkeepDragons(state));
	}
}
