package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Muse Vessel")
@Types({Type.ARTIFACT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.DISSENSION, r = Rarity.RARE)})
@ColorIdentity({})
public final class MuseVessel extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("MuseVessel", "Choose a card exiled with Muse Vessel.", true);

	public static final class MuseVesselAbility0 extends ActivatedAbility
	{
		public MuseVesselAbility0(GameState state)
		{
			super(state, "(3), (T): Target player exiles a card from his or her hand. Activate this ability only any time you could cast a sorcery.");
			this.setManaCost(new ManaPool("(3)"));
			this.costsTap = true;

			Target t = this.addTarget(Players.instance(), "target player");

			SetGenerator fromTheirHand = InZone.instance(HandOf.instance(targetedBy(t)));
			EventFactory exile = exile(targetedBy(t), fromTheirHand, 1, "Target player exiles a card from his or her hand.");
			exile.setLink(this);
			this.addEffect(exile);
			this.getLinkManager().addLinkClass(MuseVesselAbility1.class);

			this.activateOnlyAtSorcerySpeed();
		}
	}

	/**
	 * @eparam CAUSE: muse vessel's trigger
	 * @eparam PLAYER: "you"
	 * @eparam OBJECT: objects exiled by muse vessel
	 */
	public static final EventType MUSE_VESSEL_EVENT = new EventType("MUSE_VESSEL_EVENT")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player you = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Set objects = parameters.get(Parameter.OBJECT);

			java.util.Collection<GameObject> chosen = you.sanitizeAndChoose(game.actualState, 1, objects.getAll(GameObject.class), PlayerInterface.ChoiceType.OBJECTS, REASON);

			ContinuousEffect.Part permission = new ContinuousEffect.Part(ContinuousEffectType.MAY_PLAY_LOCATION);
			permission.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(chosen));
			permission.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(You.instance())));
			createFloatingEffect("You may play the chosen card this turn.", permission).createEvent(game, event.getSource()).perform(event, true);

			event.setResult(Empty.set);
			return true;
		}
	};

	public static final class MuseVesselAbility1 extends ActivatedAbility
	{
		public MuseVesselAbility1(GameState state)
		{
			super(state, "(1): Choose a card exiled with Muse Vessel. You may play that card this turn.");
			this.setManaCost(new ManaPool("(1)"));

			this.getLinkManager().addLinkClass(MuseVesselAbility0.class);
			EventFactory effect = new EventFactory(MUSE_VESSEL_EVENT, "Choose a card exiled with Muse Vessel. You may play that card this turn.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			effect.parameters.put(EventType.Parameter.OBJECT, ChosenFor.instance(LinkedTo.instance(This.instance())));
			this.addEffect(effect);
		}
	}

	public MuseVessel(GameState state)
	{
		super(state);

		// (3), (T): Target player exiles a card from his or her hand. Activate
		// this ability only any time you could cast a sorcery.
		this.addAbility(new MuseVesselAbility0(state));

		// (1): Choose a card exiled with Muse Vessel. You may play that card
		// this turn.
		this.addAbility(new MuseVesselAbility1(state));
	}
}
