package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Gravecrawler")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("B")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class Gravecrawler extends Card
{
	public static final class GravecrawlerAbility1 extends StaticAbility
	{
		public GravecrawlerAbility1(GameState state)
		{
			super(state, "You may cast Gravecrawler from your graveyard as long as you control a Zombie.");

			SetGenerator inGraveyard = InZone.instance(GraveyardOf.instance(You.instance()));

			ContinuousEffect.Part playEffect = new ContinuousEffect.Part(ContinuousEffectType.MAY_CAST_LOCATION);
			playEffect.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(This.instance(), inGraveyard));
			playEffect.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(You.instance())));
			this.addEffectPart(playEffect);

			this.canApply = Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.ZOMBIE));
		}
	}

	public Gravecrawler(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Gravecrawler can't block.
		this.addAbility(new org.rnd.jmagic.abilities.CantBlock(state, this.getName()));

		// You may cast Gravecrawler from your graveyard as long as you control
		// a Zombie.
		this.addAbility(new GravecrawlerAbility1(state));
	}
}
