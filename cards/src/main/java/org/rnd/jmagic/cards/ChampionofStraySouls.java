package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Champion of Stray Souls")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.SKELETON})
@ManaCost("4BB")
@ColorIdentity({Color.BLACK})
public final class ChampionofStraySouls extends Card
{
	public static final class ChampionofStraySoulsAbility0 extends ActivatedAbility
	{
		public ChampionofStraySoulsAbility0(GameState state)
		{
			super(state, "(3)(B)(B), (T), Sacrifice X other creatures: Return X target creature cards from your graveyard to the battlefield.");
			this.setManaCost(new ManaPool("(3)(B)(B)"));
			this.costsTap = true;

			SetGenerator X = ValueOfX.instance(This.instance());
			SetGenerator otherCreatures = RelativeComplement.instance(HasType.instance(Type.CREATURE), ABILITY_SOURCE_OF_THIS);
			EventFactory sacrifice = sacrifice(You.instance(), X, otherCreatures, "sacrifice X other creatures");
			sacrifice.usesX();
			this.addCost(sacrifice);

			SetGenerator deadThings = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance())));
			SetGenerator target = targetedBy(this.addTarget(deadThings, "X target creature cards from your graveyard").setSingleNumber(X));
			this.addEffect(putOntoBattlefield(target, "Return X target creature cards from your graveyard to the battlefield."));
		}
	}

	public static final class ChampionofStraySoulsAbility1 extends ActivatedAbility
	{
		public ChampionofStraySoulsAbility1(GameState state)
		{
			super(state, "(5)(B)(B): Put Champion of Stray Souls on top of your library from your graveyard.");
			this.setManaCost(new ManaPool("(5)(B)(B)"));
			this.activateOnlyFromGraveyard();
			this.addEffect(putOnTopOfLibrary(ABILITY_SOURCE_OF_THIS, "Put Champion of Stray Souls on top of your library from your graveyard."));
		}
	}

	public ChampionofStraySouls(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// (3)(B)(B), (T), Sacrifice X other creatures: Return X target creature
		// cards from your graveyard to the battlefield.
		this.addAbility(new ChampionofStraySoulsAbility0(state));

		// (5)(B)(B): Put Champion of Stray Souls on top of your library from
		// your graveyard.
		this.addAbility(new ChampionofStraySoulsAbility1(state));
	}
}
