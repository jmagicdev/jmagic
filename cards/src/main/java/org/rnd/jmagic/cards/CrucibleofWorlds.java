package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crucible of Worlds")
@Types({Type.ARTIFACT})
@ManaCost("3")
@ColorIdentity({})
public final class CrucibleofWorlds extends Card
{
	public static final class GraveyardLands extends StaticAbility
	{
		public GraveyardLands(GameState state)
		{
			super(state, "You may play land cards from your graveyard.");

			SetGenerator lands = HasType.instance(Type.LAND);
			SetGenerator controller = ControllerOf.instance(This.instance());
			SetGenerator inControllersGraveyard = InZone.instance(GraveyardOf.instance(controller));
			SetGenerator landsInYourGraveyard = Intersect.instance(lands, inControllersGraveyard);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MAY_PLAY_LOCATION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, landsInYourGraveyard);
			part.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(controller)));
			this.addEffectPart(part);
		}
	}

	public CrucibleofWorlds(GameState state)
	{
		super(state);

		this.addAbility(new GraveyardLands(state));
	}
}
