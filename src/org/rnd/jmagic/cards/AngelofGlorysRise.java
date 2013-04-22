package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Angel of Glory's Rise")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("5WW")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class AngelofGlorysRise extends Card
{
	public static final class AngelofGlorysRiseAbility1 extends EventTriggeredAbility
	{
		public AngelofGlorysRiseAbility1(GameState state)
		{
			super(state, "When Angel of Glory's Rise enters the battlefield, exile all Zombies, then return all Human creature cards from your graveyard to the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			this.addEffect(exile(Intersect.instance(Permanents.instance(), HasSubType.instance(SubType.ZOMBIE)), "Exile all Zombies."));

			this.addEffect(putOntoBattlefield(Intersect.instance(InZone.instance(GraveyardOf.instance(You.instance())), HasSubType.instance(SubType.HUMAN), HasType.instance(Type.CREATURE)), "Return all Human creature cards from your graveyard to the battlefield."));
		}
	}

	public AngelofGlorysRise(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(6);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Angel of Glory's Rise enters the battlefield, exile all Zombies,
		// then return all Human creature cards from your graveyard to the
		// battlefield.
		this.addAbility(new AngelofGlorysRiseAbility1(state));
	}
}
