package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Skaab Ruinator")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR, SubType.ZOMBIE})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE})
public final class SkaabRuinator extends Card
{
	public static final class SkaabRuinatorAbility2 extends StaticAbility
	{
		public SkaabRuinatorAbility2(GameState state)
		{
			super(state, "You may cast Skaab Ruinator from your graveyard.");

			SetGenerator inGraveyard = InZone.instance(GraveyardOf.instance(You.instance()));

			ContinuousEffect.Part playEffect = new ContinuousEffect.Part(ContinuousEffectType.MAY_PLAY_LOCATION);
			playEffect.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			playEffect.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(You.instance())));
			this.addEffectPart(playEffect);

			this.canApply = Intersect.instance(This.instance(), inGraveyard);
		}
	}

	public SkaabRuinator(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(6);

		// As an additional cost to cast Skaab Ruinator, exile three creature
		// cards from your graveyard.
		this.addCost(exile(You.instance(), Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))), 3, "exile three creature cards from your graveyard"));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// You may cast Skaab Ruinator from your graveyard.
		this.addAbility(new SkaabRuinatorAbility2(state));
	}
}
