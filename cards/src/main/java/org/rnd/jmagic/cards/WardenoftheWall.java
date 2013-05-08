package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Warden of the Wall")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class WardenoftheWall extends Card
{
	public static final class WardenoftheWallAbility2 extends StaticAbility
	{
		public WardenoftheWallAbility2(GameState state)
		{
			super(state, "As long as it's not your turn, Warden of the Wall is a 2/3 Gargoyle artifact creature with flying.");
			SetGenerator itsYourTurn = Intersect.instance(OwnerOf.instance(CurrentTurn.instance()), You.instance());
			this.canApply = Both.instance(this.canApply, Not.instance(itsYourTurn));

			ContinuousEffect.Part ptPart = setPowerAndToughness(This.instance(), 2, 3);

			ContinuousEffect.Part typesPart = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			typesPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			typesPart.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.GARGOYLE, Type.ARTIFACT, Type.CREATURE));

			ContinuousEffect.Part abilityPart = addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Flying.class);

			this.addEffectPart(ptPart, typesPart, abilityPart);
		}
	}

	public WardenoftheWall(GameState state)
	{
		super(state);

		// Warden of the Wall enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// As long as it's not your turn, Warden of the Wall is a 2/3 Gargoyle
		// artifact creature with flying.
		this.addAbility(new WardenoftheWallAbility2(state));
	}
}
