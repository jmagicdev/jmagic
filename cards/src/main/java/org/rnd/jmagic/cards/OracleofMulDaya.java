package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Oracle of Mul Daya")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.SHAMAN})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class OracleofMulDaya extends Card
{
	public static final class LibraryLands extends StaticAbility
	{
		public LibraryLands(GameState state)
		{
			super(state, "You may play the top card of your library if it's a land card.");

			SetGenerator yourLibrary = LibraryOf.instance(You.instance());
			SetGenerator land = Intersect.instance(TopCards.instance(1, yourLibrary), HasType.instance(Type.LAND));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MAY_PLAY_LOCATION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, land);
			part.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(You.instance())));
			this.addEffectPart(part);
		}
	}

	public OracleofMulDaya(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.PlayExtraLands.Final(state, 1, "You may play an additional land on each of your turns."));

		this.addAbility(new org.rnd.jmagic.abilities.RevealTopOfLibrary(state));

		this.addAbility(new LibraryLands(state));
	}
}
